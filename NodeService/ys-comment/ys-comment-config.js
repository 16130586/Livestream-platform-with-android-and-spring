module.exports = {
    kafka: {
        kafkaHost: '127.0.0.1:9092',
        groupId: 'GetCommentGroup',
        sessionTimeout: 15000,
        fromOffset: 'latest',
        commitOffsetsOnFirstJoin: true, 
        outOfRangeOffset: 'latest',
    },
    server: {
        port: 3030,
        socketCheckTime: 10000
    },
    producer: {
        kafka_topic: 'M_EVENT',
        kafka_server: '127.0.0.1:2181',
    }
};