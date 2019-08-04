package com.ampos.restaurant.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.MimeTypeUtils;

import com.ampos.restaurant.BaseTestCase;
import com.ampos.restaurant.model.BillId;
import com.ampos.restaurant.model.BillOrder;
import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.model.dto.BillOrderDto;
import com.ampos.restaurant.model.dto.BillReportDto;
import com.ampos.restaurant.repository.BillOrderRepository;
import com.ampos.restaurant.repository.MenuItemRepository;
import com.ampos.restaurant.util.DateUtil;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BillOrderControllerTestCase extends BaseTestCase {

	@Autowired
	private BillOrderRepository billOrderRepository;
	@Autowired
	private MenuItemRepository muItemRepository;

	@Before
	public void setUp() throws Exception {
		// insert menu data

		List<MenuItem> menuDatas = new ArrayList<MenuItem>();
		menuDatas.add(new MenuItem(1, "Chicken Tom Yum Pizza",
				"All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
				"Italian Thai", 300));
		menuDatas.add(new MenuItem(2, "Kimchi", "Traditional side dish made from salted and fermented vegetables",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
				"Italian Thai", 400));
		muItemRepository.saveAll(menuDatas);
		// insert bill data
		billOrderRepository.save(new BillOrder(
				new BillId(1, "Kimchi", DateUtil.convertStringToDate("09/12/2019 11:11:11", "GMT+7:00")), 2));
		billOrderRepository.save(new BillOrder(
				new BillId(1, "Chicken Tom Yum Pizza", DateUtil.convertStringToDate("09/12/2019 11:11:11", "GMT+7:00")),
				2));
		billOrderRepository.save(new BillOrder(
				new BillId(2, "Chicken Tom Yum Pizza", DateUtil.convertStringToDate("09/12/2019 11:11:11", "GMT+7:00")),
				2));

	}

	/**
	 * Test case for create bill successfully
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void addBillOrderSuccessfully() throws IOException, Exception {

		// input
		BillOrderDto input = new BillOrderDto(2, "Kimchi", "09/08/2019 11:11:11", 1);

		MvcResult result = mockMvc
				.perform(post("/bill/").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(input)))
				.andExpect(status().is(200)).andReturn();
		assertEquals("Successfully create bill", result.getResponse().getContentAsString());
		// check database
		BillId id = new BillId(2, "Kimchi", DateUtil.convertStringToDate("09/08/2019 11:11:11", "GMT+7:00"));
		BillOrder expectedData = new BillOrder(id, 1);
		assertEquals(expectedData, billOrderRepository.findById(id).get());

	}

	/**
	 * Test case for create bill fail
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void addBillOrderFail() throws IOException, Exception {

		// input
		BillOrderDto input = new BillOrderDto(1, "Kimchi", "09/12/2019 11:11:11", 2);

		MvcResult result = mockMvc
				.perform(post("/bill/").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(input)))
				.andExpect(status().is(417)).andReturn();
		assertEquals("Failed to create bill", result.getResponse().getContentAsString());

	}

	/**
	 * Test case for get bill by id
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getBillOrderSuccessfully() throws IOException, Exception {
		// expected data

		// expected data
		List<BillReportDto> expectedData = new ArrayList<>();
		expectedData.add(new BillReportDto("Chicken Tom Yum Pizza", 2, 600));
		expectedData.add(new BillReportDto("Kimchi", 2, 800));

		MvcResult result = mockMvc.perform(get("/bill/1")).andExpect(status().is(200)).andReturn();
		assertEquals(result.getResponse().getContentAsString(), asJsonString(expectedData));

	}

	/**
	 * Test case for get all
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getBillOrderAllSuccessfully() throws IOException, Exception {
		// expected data
		List<BillReportDto> expectedData = new ArrayList<>();
		expectedData.add(new BillReportDto("Kimchi", 2, 800));
		expectedData.add(new BillReportDto("Chicken Tom Yum Pizza", 2, 600));
		expectedData.add(new BillReportDto("Chicken Tom Yum Pizza", 2, 600));

		MvcResult result = mockMvc.perform(get("/bill/all")).andExpect(status().is(200)).andReturn();
		assertEquals(asJsonString(expectedData), result.getResponse().getContentAsString());

	}

	/**
	 * Test case for update bill successfully
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void updateBillOrderSuccessfully() throws IOException, Exception {

		// input
		BillOrderDto input = new BillOrderDto(1, "Kimchi", "09/12/2019 11:11:11", 3);

		MvcResult result = mockMvc
				.perform(put("/bill/").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(input)))
				.andExpect(status().is(200)).andReturn();
		assertEquals("Successfully update bill", result.getResponse().getContentAsString());
		// check database
		BillId id = new BillId(1, "Kimchi", DateUtil.convertStringToDate("09/12/2019 11:11:11", "GMT+7:00"));
		BillOrder expectedData = new BillOrder(id, 3);
		assertEquals(expectedData, billOrderRepository.findById(id).get());

	}

	/**
	 * Test case for update bill failed
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void updateBillOrderFail() throws IOException, Exception {

		// input
		BillOrderDto input = new BillOrderDto(2, "Kimchi", "09/12/2019 11:11:11", 2);

		MvcResult result = mockMvc
				.perform(put("/bill/").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(input)))
				.andExpect(status().is(417)).andReturn();
		assertEquals("Failed to update bill", result.getResponse().getContentAsString());

	}

}
