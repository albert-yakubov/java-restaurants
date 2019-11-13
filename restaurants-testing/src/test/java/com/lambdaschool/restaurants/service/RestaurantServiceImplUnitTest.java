package com.lambdaschool.restaurants.service;

import com.lambdaschool.restaurants.RestaurantsApplication;
import com.lambdaschool.restaurants.model.Menu;
import com.lambdaschool.restaurants.model.Restaurant;
import com.lambdaschool.restaurants.model.RestaurantPayments;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
//TODO 1 for testing rest service implementation
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestaurantsApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestaurantServiceImplUnitTest
{
    // todo 2 set up repos that are used for restaurants
    @Autowired
    private RestaurantService restaurantService;
    //todo 3 set up Mockito
    @Before
    public void AsetUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void BtearDown() throws Exception
    {
    }
//todo 4 expected 3 restaurants to return
    @Test
    public void CfindAll()

    {
        assertEquals(3, restaurantService.findAll().size());
    }

    @Test
    public void DfindRestaurantById()
    {
        assertEquals("Bird Cafe Test", restaurantService.findRestaurantById(10).getName());
    }

    @Test
    public void EfindRestaurantByName()
    {
        assertEquals("Apple Test", restaurantService.findRestaurantByName("Apple Test").getName());
    }

    @Test
    public void EAupdate()
    {
        ArrayList<RestaurantPayments> thisPay = new ArrayList<>();
        Restaurant r1 = new Restaurant(null,
                null,
                null, "ZZ", null,
                thisPay);
        r1.setRestaurantid(10);

        Restaurant updatedR1 = restaurantService.update(r1, 10);

        assertEquals("ZZ", updatedR1.getState());
    }
//todo 5.1 delete not found (because we re expecting exception add expecting)
    @Test (expected = EntityNotFoundException.class)
    public void FdeleteNotFound()
    {
        restaurantService.delete(100);
        assertEquals(2, restaurantService.findAll().size());
    }

//todo 5 delete
    @Test
    public void GdeleteFound()
    {
        restaurantService.delete(10);
        assertEquals(2, restaurantService.findAll().size());
    }

//todo 4 copy over added restaurant from seed data
    @Test
    public void Hsave()
    {
        ArrayList<RestaurantPayments> thisPay = new ArrayList<>();
        String rest3Name = "Number 1 Test Eats";
        Restaurant r3 = new Restaurant(rest3Name,
                "565 Side Test Avenue",
                "Village", "ST", "555-123-1555",
                thisPay);
        r3.getMenus().add(new Menu("Pizza", 15.15, r3));
//todo 4.1 checking if it saves in repo
        Restaurant addRestaurant = restaurantService.save(r3);
//todo 4.2 if addRestaurant contains something
        assertNotNull(addRestaurant);
// todo 4.3 assuming that findbyid works, check if assertEquals to added restaurant
        Restaurant foundRestaurant = restaurantService.findRestaurantById(addRestaurant.getRestaurantid());
        assertEquals(addRestaurant.getName(), foundRestaurant.getName());
    }
}