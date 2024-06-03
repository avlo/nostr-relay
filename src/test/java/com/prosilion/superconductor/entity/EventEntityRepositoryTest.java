package com.prosilion.superconductor.entity;

import com.prosilion.superconductor.repository.EventEntityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(
    showSql = false  // on by default
    , includeFilters = @ComponentScan.Filter( // quicker tests, allegedly
    type = FilterType.ASSIGNABLE_TYPE,
    classes = EventEntityRepository.class)
)

// annotation used in conjunction with non-static @BeforeAll
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

// note: placing @Sql at class level- in combo with @BeforeAll/setup() calling save() seems to execute for every test method, even with
//  executionPhase = ExecutionPhase.BEFORE_TEST_CLASS set.  fix was to instead use @Sql on @BeforeAll setup() method
//@Sql(
//    scripts = {"/data.sql"},
//    executionPhase = ExecutionPhase.BEFORE_TEST_CLASS
//) // class level @Sql
@DirtiesContext
class EventEntityRepositoryTest {
  public static final String SIGNATURE = "86f25c161fec51b9e441bdb2c09095d5f8b92fdce66cb80d9ef09fad6ce53eaa14c5e16787c42f5404905536e43ebec0e463aee819378a4acbe412c533e60546";
  public static final String EVENT_ID = "5f66a36101d3d152c6270e18f5622d1f8bce4ac5da9ab62d7c3cc0006e5914cc";
  public static final String PUB_KEY = "bbbd79f81439ff794cf5ac5f7bff9121e257f399829e472c7a14d3e86fe76984";
  public static final String CONTENT = "1111111111";
  public static final Integer KIND = 1;
  public static final Integer NIP = 1;
  public static final long CREATED_AT = 1717357053050L;

  @Autowired
  EventEntityRepository eventEntityRepository;

  // below gets executed only once
  // @Sql(scripts = {"/data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
  // non-static @BeforeAll used in conjunction with @TestInstance above
  @BeforeAll
  void setUp() {
    eventEntityRepository.save(new EventEntity(EVENT_ID, KIND, NIP, PUB_KEY, CREATED_AT, SIGNATURE, CONTENT));
  }

//    @AfterEach
//    void tearDown() {
//    }

  @Test
  void getCountEquals1() {
    assertEquals(1, eventEntityRepository.findAll().size());
  }

  @Test
  void getIdEquals1() {
    assertNotNull(eventEntityRepository.findById(1L));
    assertEquals(1, eventEntityRepository.findAll().stream().findFirst().orElseThrow().getId());
  }

  @Test
  void testRecordNotExist() {
    Optional<EventEntity> eventEntity = eventEntityRepository.findById(0L);
    assertThrows(NoSuchElementException.class, eventEntity::orElseThrow);
  }

  @Test
  void testGetAllFields() {
    List<EventEntity> eventEntityList = eventEntityRepository.findByContent(CONTENT);
    assertDoesNotThrow(() -> eventEntityList.stream().findFirst());
    assertEquals(SIGNATURE, eventEntityRepository.findByContent(CONTENT).stream().findFirst().orElseThrow().getSignature());
    assertEquals(EVENT_ID, eventEntityRepository.findByContent(CONTENT).stream().findFirst().orElseThrow().getEventId());
    assertEquals(PUB_KEY, eventEntityRepository.findByContent(CONTENT).stream().findFirst().orElseThrow().getPubKey());
    assertEquals(CONTENT, eventEntityRepository.findByContent(CONTENT).stream().findFirst().orElseThrow().getContent());
    assertEquals(KIND, eventEntityRepository.findByContent(CONTENT).stream().findFirst().orElseThrow().getKind());
    assertEquals(NIP, eventEntityRepository.findByContent(CONTENT).stream().findFirst().orElseThrow().getNip());
    assertEquals(CREATED_AT, eventEntityRepository.findByContent(CONTENT).stream().findFirst().orElseThrow().getCreatedAt());
  }

  @Test
  void testTestClassCreatedEntity() {
    assertDoesNotThrow(() -> eventEntityRepository.findByContent(CONTENT));
  }
}