/**
 * Copyright © 2014 - 2017 Leipzig University (Database Research Group)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradoop.flink.model.impl.operators.cloning.functions;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.functions.FunctionAnnotation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.gradoop.common.model.impl.pojo.Edge;
import org.gradoop.common.model.impl.id.GradoopId;

/**
 * Joins edges with a Tuple2 that contains the id of the original edge
 * source in its first field and the id of the new edge source vertex in its
 * second.
 * The output is an edge with updated source id.
 *
 * @param <E> EPGM edge type
 */
@FunctionAnnotation.ForwardedFieldsSecond("f1->sourceId")
public class EdgeSourceUpdateJoin<E extends Edge>
  implements JoinFunction<E, Tuple2<GradoopId, GradoopId>, E> {
  /**
   * {@inheritDoc}
   */
  @Override
  public E join(E e, Tuple2<GradoopId, GradoopId> vertexTuple) {
    e.setSourceId(vertexTuple.f1);
    return e;
  }
}
