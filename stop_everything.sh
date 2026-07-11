#!/bin/bash
for svc in \
Eureka-Gestion \
Gateway-Gestion \
Usuarios-Gestion \
Solicitudes-Gestion \
Incidentes-Gestion \
Cambios-Gestion
do
    railway down --service "$svc" -y
done