/*
 * This file is part of Gradoop.
 *
 * Gradoop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Gradoop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Gradoop. If not, see <http://www.gnu.org/licenses/>.
 */

package org.gradoop.model.impl.operators.count;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.gradoop.model.impl.functions.bool.Equals;
import org.gradoop.model.impl.functions.tuple.ValueOfTuple1;

public class Count {

  public static <T> DataSet<Long> count(DataSet<T> dataSet) {
    return dataSet
      .map(new Tuple1With1L<T>())
      .union(dataSet.getExecutionEnvironment().fromElements(new Tuple1<>(0L)))
      .sum(0)
      .map(new ValueOfTuple1<Long>());
  }

  public static <T> DataSet<Boolean> isEmpty(DataSet<T> dataSet) {
    return Equals.cross(count(dataSet),
      dataSet.getExecutionEnvironment().fromElements(0L));
  }

  public static <T> DataSet<Tuple2<T,Long>> groupBy(DataSet<T> dataSet) {
    return dataSet
      .map(new Tuple2WithObjectAnd1L<T>())
      .groupBy(0)
      .sum(1);
  }
}
