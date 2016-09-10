#!/usr/bin/env bash
process_id=`cat nohupPid.txt`;
kill -9 $process_id;