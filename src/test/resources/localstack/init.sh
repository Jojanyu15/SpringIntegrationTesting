#!/bin/sh

awslocal sqs create-queue --queue-name booking-queue

echo "Initialized."
