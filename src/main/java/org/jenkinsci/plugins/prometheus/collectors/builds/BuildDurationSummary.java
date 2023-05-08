package org.jenkinsci.plugins.prometheus.collectors.builds;

import hudson.model.Run;
import io.prometheus.client.Summary;
import org.jenkinsci.plugins.prometheus.collectors.BaseMetricCollector;

public class BuildDurationSummary extends BuildsMetricCollector<Run, Summary> {

    public BuildDurationSummary(String[] labelNames, String namespace, String subSystem) {
        super(labelNames, namespace, subSystem);
    }

    @Override
    protected Summary initCollector() {
        return Summary.build()
                .name(calculateName("duration_milliseconds_summary"))
                .subsystem(subsystem)
                .namespace(namespace)
                .labelNames(labelNames)
                .help("Summary of Jenkins build times in milliseconds by Job")
                .create();
    }
    @Override
    public void calculateMetric(Run jenkinsObject, String[] labelValues) {
        if (!jenkinsObject.isBuilding()) {
            long duration = jenkinsObject.getDuration();
            this.collector.labels(labelValues).observe(duration);
        }
    }
}
