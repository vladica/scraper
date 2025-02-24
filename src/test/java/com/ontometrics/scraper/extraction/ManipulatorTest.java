package com.ontometrics.scraper.extraction;

import static com.ontometrics.scraper.HtmlSample.PagedListingTable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import net.htmlparser.jericho.Source;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ontometrics.scraper.TagOccurrence;

public class ManipulatorTest {

	private static final Logger log = LoggerFactory.getLogger(ManipulatorTest.class);

	private MockManipulator mockExtractionAgent;

	private SplicingExtractor splicingExtractor;

	@Before
	public void setup() {
		mockExtractionAgent = new MockManipulator();
		splicingExtractor = new SplicingExtractor(SpliceOperation.After, new TagOccurrence.Builder()
				.tag("table")
				.occurrence(3)
				.build());
		splicingExtractor.setSuccessor(mockExtractionAgent);

	}

	@Test
	public void canSpliceSource() {

		Source pageSource = PagedListingTable.getSource();
		assertThat(pageSource, is(notNullValue()));
		splicingExtractor.execute(pageSource);
		Source result = mockExtractionAgent.getResult();

		log.info("result after splice: {}", result);

		assertThat(result.length(), is(greaterThan(0)));
		assertThat(result.toString().startsWith("<table"), is(true));

	}

	@Test
	public void canChainTwoCommands() {

	}

}
