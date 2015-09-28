/*
 * LensKit, an open source recommender systems toolkit.
 * Copyright 2010-2014 LensKit Contributors.  See CONTRIBUTORS.md.
 * Work on LensKit has been funded by the National Science Foundation under
 * grants IIS 05-34939, 08-08692, 08-12148, and 10-17697.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.lenskit.eval.traintest.recommend;

import it.unimi.dsi.fastutil.longs.Long2DoubleMap;
import org.grouplens.lenskit.data.history.UserHistory;
import org.grouplens.lenskit.eval.metrics.ResultColumn;
import org.grouplens.lenskit.util.statistics.MeanAccumulator;
import org.lenskit.api.ResultList;
import org.lenskit.data.events.Event;
import org.lenskit.eval.traintest.AlgorithmInstance;
import org.lenskit.eval.traintest.DataSet;
import org.lenskit.eval.traintest.metrics.MetricResult;
import org.lenskit.eval.traintest.metrics.TypedMetricResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Metric that measures how long a TopN list actually is.
 * @author <a href="http://www.grouplens.org">GroupLens Research</a>
 */
public class LengthTopNMetric extends TopNMetric<MeanAccumulator> {
    /**
     * Construct a new length metric.
     */
    public LengthTopNMetric() {
        super(LengthResult.class, LengthResult.class);
    }

    @Nonnull
    @Override
    public MetricResult measureUser(UserHistory<Event> user, Long2DoubleMap ratings, ResultList recommendations, MeanAccumulator context) {
        int n = recommendations.size();
        context.add(n);
        return new LengthResult(n);
    }

    @Nullable
    @Override
    public MeanAccumulator createContext(AlgorithmInstance algorithm, DataSet dataSet, org.lenskit.api.Recommender recommender) {
        return new MeanAccumulator();
    }

    @Nonnull
    @Override
    public MetricResult getAggregateMeasurements(MeanAccumulator context) {
        return new LengthResult(context.getMean());
    }

    public static class LengthResult extends TypedMetricResult {
        @ResultColumn("TopN.ActualLength")
        public final double length;

        public LengthResult(double len) {
            length = len;
        }
    }
}
