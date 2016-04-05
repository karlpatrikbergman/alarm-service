package se.infinera.metro.microservice.alarm;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import se.infinera.metro.microservice.alarm.entity.Node;
import se.infinera.metro.microservice.alarm.repository.AlarmRepository;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;
@SuppressWarnings("SpringJavaAutowiringInspection")
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 15)
public class PopulateDatabaseRunner implements CommandLineRunner {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Override
    public void run(String... args) throws Exception {
        nodeRepository.save(
                Node.builder()
                        .ipAddress("172.17.0.2")
                        .port(80)
                        .userName("root")
                        .password("root")
                        .build()
        );

//        Author author = Author.builder()
//                .firstName("Patrik")
//                .lastName("Bergman")
//                .books(null)
//                .build();
//        authorRepository.save(author);
//
//        Reviewer reviewer1 = Reviewer.builder()
//                .firstName("King")
//                .lastName("Diamond")
//                .build();
//        reviewerRepository.save(reviewer1);
//
//        Reviewer reviewer2 = Reviewer.builder()
//                .firstName("Lauren")
//                .lastName("Hill")
//                .build();
//        reviewerRepository.save(reviewer2);
//
//        Publisher publisher = Publisher.builder()
//                .name("Nordstedths")
//                .build();
//        publisherRepository.save(publisher);
//
//        Book book = Book.builder()
//                .title("Varats olidliga tyngd")
//                .description("En bok om hur olidligt tungt det kan vara att vara")
//                .isbn("978-1-78528-415-1")
//                .author(author)
//                .publisher(publisher)
//                .reviewers(Arrays.asList(reviewer1, reviewer2))
//                .build();
//        bookRepository.save(book);
    }

//    @Scheduled(initialDelay = 1000, fixedRate = 10000)
//    public void run() {
//        logger.info("Number of books: " + bookRepository.count());
//    }
}
