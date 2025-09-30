package com.exemplo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockTests {

    @Mock
    List<String> mockList;

    @Test
    void testMockSizeReturn() {
        when(mockList.size()).thenReturn(5);
        assertEquals(5, mockList.size());
    }

    @Test
    void testMockGetReturn() {
        when(mockList.get(0)).thenReturn("primeiro");
        assertEquals("primeiro", mockList.get(0));
    }

    @Test
    void testMockVerifyInvocation() {
        mockList.add("x");
        verify(mockList).add("x");
    }

    @Test
    void testMockThrowExceptionOnGet() {
        when(mockList.get(anyInt())).thenThrow(new IndexOutOfBoundsException("índice inválido"));
        assertThrows(IndexOutOfBoundsException.class, () -> mockList.get(100));
    }

    @Test
    void testMockThenAnswer() {
        when(mockList.get(anyInt())).thenAnswer(invocation -> {
            Integer idx = invocation.getArgument(0);
            return "valor-" + idx;
        });
        assertEquals("valor-2", mockList.get(2));
    }

    @Test
    void testSpyOnArrayList() {
        List<String> real = new ArrayList<>();
        List<String> spy = spy(real);
        spy.add("a");
        spy.add("b");
        verify(spy).add("a");
        assertEquals(2, spy.size());
        // stub size
        when(spy.size()).thenReturn(10);
        assertEquals(10, spy.size());
    }

    @Test
    void testDoThrowOnAdd() {
        doThrow(new RuntimeException("erro ao adicionar")).when(mockList).add("fail");
        assertThrows(RuntimeException.class, () -> mockList.add("fail"));
    }

    @Test
    void testVerifyNoMoreInteractions() {
        mockList.clear();
        verify(mockList).clear();
        verifyNoMoreInteractions(mockList);
    }

    @Test
    void testArgumentCaptor() {
        mockList.add("capturado");
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockList).add(captor.capture());
        assertEquals("capturado", captor.getValue());
    }

    @Test
    void testMockIntegrationWithAppConverter() throws IOException {
        @SuppressWarnings("unchecked")
        List<Pessoa> pessoas = (List<Pessoa>) mock(List.class);
        Pessoa p = new Pessoa("MockUser", 33, "Cidade");
        when(pessoas.size()).thenReturn(1);
        when(pessoas.isEmpty()).thenReturn(false);
        pessoas.add(p);
        verify(pessoas).add(p);
        assertEquals(1, pessoas.size());
    }
}
