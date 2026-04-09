#!/bin/bash

sudo ./mvnw package

sudo docker build -t mragravaine/socialsonic:large -f Dockerfile .

sudo docker build -t mragravaine/socialsonic:graalvm-upx -f graalvm-upx.Dockerfile .