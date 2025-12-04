#!/bin/bash
./compile.sh
java com/app/main/Main
find -type f -name "*.class" -delete