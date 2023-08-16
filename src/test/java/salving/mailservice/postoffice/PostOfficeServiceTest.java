package salving.mailservice.postoffice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostOfficeServiceTest {

    @Autowired
    PostOfficeService postOfficeService;

    @MockBean
    PostOfficeRepository postOfficeRepository;

    private static PostOffice validPostOffice() {
        return new PostOffice("100000", "testOffice", "testOfficeAddress");
    }

    @Test
    void registerPostOfficeReturnsPostOffice() {
        // given
        PostOffice postOffice = validPostOffice();

        when(postOfficeRepository.save(any())).thenReturn(postOffice);

        // when
        postOfficeService.registerPostOffice(postOffice);

        // then
        verify(postOfficeRepository).save(any());
    }
}