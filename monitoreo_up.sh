#!/bin/bash
set -e

echo "Waking up Loki..."
until curl -fs https://loki-production-af95.up.railway.app/ready >/dev/null; do
    sleep 5
done
echo "Loki is ready."

echo "Starting Prometheus..."
railway up -d --service Prometheus

echo "Waiting for Prometheus..."
until curl -fs https://prometheus-production-1411.up.railway.app/-/healthy >/dev/null; do
    sleep 5
done
echo "Prometheus is ready."

echo "Starting Alertmanager..."
railway up -d --service Alert_manager

echo "Waiting for Alertmanager..."
until curl -fs https://alertmanager-production-305a.up.railway.app/-/healthy >/dev/null; do
    sleep 5
done
echo "Alertmanager is ready."

echo "Waking up Grafana..."
until curl -fs https://grafana-production-8897.up.railway.app/api/health >/dev/null; do
    sleep 5
done
echo "Grafana is ready."

echo "Monitoring stack is ready."