#!/usr/bin/env sh

workdir=`pwd`
dirname $0|grep "^/" >/dev/null
if [ $? -eq 0 ];then
   workdir=`dirname $0`
else
    dirname $0|grep "^\." >/dev/null
    retval=$?
    if [ $retval -eq 0 ];then
        workdir=`dirname $0|sed "s#^.#$workdir#"`
    else
        workdir=`dirname $0|sed "s#^#$workdir/#"`
    fi
fi

cd $workdir
echo "begin to shutdown..."
#根据端口号查一下pid
PID=$(ps -ef   |grep java |grep -v "grep" |awk '{print $2}')
if [ ! -n "$PID" ];
  then
    echo ""
    echo "pid is not exist"
    exit 1
fi
kill $PID

#看看进程还在吗
UP_STATUS=$(ps -ef   |grep java |grep -v "grep" |awk '{print $2}' | wc -l)

if (($UP_STATUS <= 0));
  then
    echo "shutdown succesful..."
  else
    echo ""
    echo "pid = $PID"
    TIME_OUT=90
    TIME_COUNT=0
    while [ $TIME_COUNT -le $TIME_OUT ]
    do
      UP_STATUS=$(ps -ef   |grep java |grep -v "grep" |awk '{print $2}' | wc -l)
      if (($UP_STATUS <= 0))
      then
        TIME_COUNT=999
      else
        TIME_COUNT=$(($TIME_COUNT+1))
      fi
      echo "waiting for shutdown ..."
      sleep 1
    done
    if (($UP_STATUS <= 0))
      then
        echo ""
        echo "shutdown successful..."
      else
        echo ""
        ps -ef | grep "$PID" | grep -v grep | grep "$SERVER_NAME"
        echo ""
        echo "shutdown failed..."
    fi

fi

#cat $workdir/../logs/pid.txt | while read line
#do
#  kill $line
#  echo ...is killing pid $line...
#  while true;
#  do
#    pro=$(ps $line | grep $line | wc -l)
#    echo '...please wait...'
#    if [ $pro -ge 1 ]
#      then
#        sleep 1
#      else break
#    fi
#  done
#done
#
#echo '...shutdown finish....'
#
#rm -rf $workdir/../logs/pid.txt
#
#touch $workdir/../logs/pid.txt
