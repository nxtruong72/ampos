package com.ampos.restaurant.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.model.dto.MenuItemDto;
import com.ampos.restaurant.repository.MenuItemRepository;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MenuControllerTestCase extends BaseTestCase {

	@Autowired
	private MenuItemRepository muItemRepository;

	@Before
	public void setUp() throws Exception {
		muItemRepository.save(new MenuItem(1, "Chicken Tom Yum Pizza",
				"All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
				"Italian Thai", 300));
		muItemRepository
				.save(new MenuItem(2, "Kimchi", "Traditional side dish made from salted and fermented vegetables",
						"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
						"Italian Thai", 400));

	}

	

	/**
	 * test case for create menu successfully
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void addMenuSuccessfully() throws IOException, Exception {
		MenuItemDto input = new MenuItemDto(3, "Oolong tea",
				"All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
				"Italian Thai", 300);
		MvcResult result = mockMvc
				.perform(post("/item/").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(input)))
				.andExpect(status().is(200)).andReturn();
		assertEquals("Successfully create item", result.getResponse().getContentAsString());
		// check database
		assertEquals(new MenuItem(3, "Oolong tea",
				"All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
				"Italian Thai", 300), muItemRepository.findById(3).get());

	}

	/**
	 * Test case of get menu
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getMenuPageSuccessfully() throws IOException, Exception {
		// expected data

		List<MenuItem> lists = new ArrayList<MenuItem>();
		lists.add(new MenuItem(1, "Chicken Tom Yum Pizza",
				"All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
				"Italian Thai", 300));
		lists.add(new MenuItem(2, "Kimchi", "Traditional side dish made from salted and fermented vegetables",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
				"Italian Thai", 400));

		MvcResult result = mockMvc.perform(get("/item")).andExpect(status().is(200)).andReturn();
		assertTrue(result.getResponse().getContentAsString().contains(asJsonString(lists)));

	}

	/**
	 * Test case for update menu in passed
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void updateSuccessfully() throws IOException, Exception {
		MenuItemDto input = new MenuItemDto(2, "Kimchi", "Chinese steamed bun",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu3.jpg",
				"Italian Thai", 200);
		MvcResult result = mockMvc
				.perform(put("/item/").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(input)))
				.andExpect(status().is(200)).andReturn();
		assertEquals("Successfully upate item", result.getResponse().getContentAsString());
		// check database
		assertEquals(new MenuItem(2, "Kimchi", "Chinese steamed bun",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu3.jpg",
				"Italian Thai", 200), muItemRepository.findByName("Kimchi").get(0));

	}

	/**
	 * Test case for update menu failed
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void updateFail() throws IOException, Exception {
		MenuItemDto input = new MenuItemDto(11, "Xiaolongbao", "Chinese steamed bun",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu3.jpg",
				"Italian Thai", 200);

		 mockMvc
				.perform(put("/item/").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(input)))
				.andExpect(status().is(417)).andReturn();

		

	}

	/**
	 * Test case for delete menu in passed
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void deleteSuccessfully() throws IOException, Exception {
		MenuItemDto input = new MenuItemDto(1, "Kimchi",
				"Traditional side dish made from salted and fermented vegetables",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
				"Italian Thai", 400);

		MvcResult result = mockMvc
				.perform(
						delete("/item/").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(input)))
				.andExpect(status().is(200)).andReturn();
		assertEquals("Successfully delete item", result.getResponse().getContentAsString());
		assertTrue(muItemRepository.findByName("Kimchi").isEmpty());

	}

	/**
	 * Test case for delete menu failed
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void deleteFail() throws IOException, Exception {
		MenuItemDto input = new MenuItemDto(4, "Chi Chi",
				"Traditional side dish made from salted and fermented vegetables",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
				"Italian Thai", 400);

		 mockMvc
				.perform(
						delete("/item/").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(asJsonString(input)))
				.andExpect(status().is(417)).andReturn();
		

	}

	/**
	 * Test case for search
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void searchSuccessfully() throws IOException, Exception {
		List<MenuItem> outputs = new ArrayList<>();
		outputs.add(new MenuItem(2, "Kimchi", "Traditional side dish made from salted and fermented vegetables",
				"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
				"Italian Thai", 400));

		MvcResult result = mockMvc.perform(get("/item/search?keyword=Kimchi")).andExpect(status().is(200)).andReturn();
		assertEquals(asJsonString(outputs), result.getResponse().getContentAsString());

	}

}
