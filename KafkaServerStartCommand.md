> docker-compose -f common.yml -f zookeeper.yml up

> echo ruok | nc localhost 2181

> docker-compose -f common.yml -f kafka_cluster.yml up

> docker-compose -f common.yml -f init_kafka.yml up



generate kafka schema -> mvn clean install form kafka-model module


(single node confluent setup)[https://www.youtube.com/watch?v=D5TSOt3hVTU]

> curl -O https://packages.confluent.io/archive/7.3/confluent-7.3.2.zip

> unzip confluent-7.3.2.zip

> vim .zshrc

+ export confluent directory path

        export CONFLUENT_HOME=~/confluent-7.3.2
        export PATH=$PATH:$CONFLUENT_HOME/bin

> confluent local services start


