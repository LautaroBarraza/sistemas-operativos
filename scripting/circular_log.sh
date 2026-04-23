#!/bin/bash


dir_base="./logs"
cant=3
t=$1

if [ find "$dir_base"/"log_app.log" -size $t  ]

