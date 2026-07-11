#!/bin/bash
set -e
for svc in \
Prometheus \
Alert_manager
do
    echo "Stopping $svc..."
    railway down --service "$svc" -y
done
echo "Monitoring stack stopped."