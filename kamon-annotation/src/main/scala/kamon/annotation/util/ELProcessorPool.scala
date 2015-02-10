/*
 * =========================================================================================
 * Copyright © 2013-2015 the kamon project <http://kamon.io/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 * =========================================================================================
 */

package kamon.annotation.util

import javax.el.ELProcessor

import kamon.annotation.resolver.PrivateFieldELResolver
import kamon.annotation.util.FastObjectPool.PoolFactory

/**
 * Convenient pool of @see ELProcessor, since it is not thread safe.
 */
object ELProcessorPool {
  private val pool = new FastObjectPool[ELProcessor](ELPoolFactory(), 5)

  def use[A](closure: WrappedProcessor ⇒ A): A = use(None)(closure)
  def useWithObject[A](obj: AnyRef)(closure: WrappedProcessor ⇒ A): A = use(Some(obj))(closure)

  private def use[A](obj: Option[AnyRef])(closure: WrappedProcessor ⇒ A): A = {
    val holder = pool.take()
    val processor = holder.getValue
    obj.map(processor.defineBean("this", _))

    try closure(new WrappedProcessor(processor)) finally pool.release(holder)
  }
}

private class ELPoolFactory() extends PoolFactory[ELProcessor] {
  override def create(): ELProcessor = {
    val processor = new ELProcessor()
    processor.getELManager.addELResolver(new PrivateFieldELResolver())
    processor
  }
}

private object ELPoolFactory {
  def apply():ELPoolFactory = new ELPoolFactory()
}
