#!/bin/bash

sudo ./mvnw package

sudo docker build -t mragravaine/socialsonic -f Dockerfile .
