module.exports = {
    kafka: {
        kafkaHost: '127.0.0.1:9092',
        groupId: 'NormalCommentGroup',
        sessionTimeout: 15000,
        fromOffset: 'latest',
        commitOffsetsOnFirstJoin: true, 
        outOfRangeOffset: 'latest',
    },
    server: {
        port: 3000,
        socketCheckTime: 100000
    },
    producer: {
        kafka_topic: 'M_EVENT',
        kafka_server: '127.0.0.1:2181'
    }
};