package github.javaguide.serialize.protostuff;

import github.javaguide.remoting.dto.RpcRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class protostuffSerializerTest {

    @Test
    public void protostuffSerializerTest() {
        RpcRequest target = RpcRequest.builder().methodName("hello")
                .parameters(new Object[]{"sayhelooloo", "sayhelooloosayhelooloo"})
                .interfaceName("github.javaguide.HelloService")
                .paramTypes(new Class<?>[]{String.class, String.class})
                .requestId(UUID.randomUUID().toString())
                .group("group1")
                .version("version1")
                .build();
        ProtostuffSerializer ProtostuffSerializer = new ProtostuffSerializer();
        byte[] bytes = ProtostuffSerializer.serialize(target);
        RpcRequest actual = ProtostuffSerializer.deserialize(bytes, RpcRequest.class);
        assertEquals(target.getGroup(), actual.getGroup());
        assertEquals(target.getVersion(), actual.getVersion());
        assertEquals(target.getRequestId(), actual.getRequestId());
    }
}