package com.study.updateol;

import com.study.updateol.avro.AvroProtocolTests;
import com.study.updateol.protobuf.ProtobufProtocolTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @Auther: liuxin
 * @Date: 2018/9/22 13:26
 * @Description: 分组测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AvroProtocolTests.class, ProtobufProtocolTests.class})
public class TestSuiteMain {
}
