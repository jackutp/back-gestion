#!/bin/bash
railway up -d --service Eureka-Gestion
echo "Waiting for Eureka..."
until curl -fs https://eureka-gestion-production.up.railway.app/actuator/health >/dev/null; do
    sleep 5
done
echo "Eureka is ready."
for svc in \
Gateway-Gestion \
Usuarios-Gestion \
Solicitudes-Gestion \
Incidentes-Gestion \
Cambios-Gestion \
Conocimiento-Gestion
do
    railway up -d --service "$svc"
done