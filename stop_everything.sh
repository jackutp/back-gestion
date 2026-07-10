#!/bin/bash
railway down --service Eureka-Gestion -y
railway down --service Gateway-Gestion -y
railway down --service Usuarios-Gestion -y
railway down --service Solicitudes-Gestion -y
railway down --service Incidentes-Gestion -y
railway down --service Cambios-Gestion -y
