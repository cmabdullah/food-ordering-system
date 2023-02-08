> docker-compose -f common.yml -f zookeeper.yml up

> echo ruok | nc localhost 2181

> docker-compose -f common.yml -f kafka_cluster.yml up

> docker-compose -f common.yml -f init_kafka.yml up

