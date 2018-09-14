package com.study.test.protobuf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;

/**
 * protobuf协议测试用例
 */
public class TestProtobufProtocol {

    @Test
    @DisplayName("测试向后兼容")
    void testBackwardCompatible() {
        try {
            ProtobufTestUtils.testSerializationWithOldProtocol();
            ProtobufTestUtils.testDeserializationWithNewProtocol();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @DisplayName("测试向前兼容")
    void testForwardCompatible() {
        try {
            ProtobufTestUtils.testSerializationWithNewProtocol();
            ProtobufTestUtils.testDeserializationWithOldProtocol();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
