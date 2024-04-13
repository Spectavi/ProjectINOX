package com.spectavi.projectinox;

import com.spectavi.projectinox.pages.BaseTestClass;
import com.spectavi.projectinox.pages.CartPage;
import com.spectavi.projectinox.pages.SearchResultsPage;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Example test for WebstaurantStore.com
 * Test Steps:
 * 1. Go to https://www.webstaurantstore.com (in base class setUp)
 * 2. Search for 'stainless work table'
 * 3. Check the search result ensuring every product has the word 'table' in its title.
 * 4. Add the last of found items to Cart.
 * 5. Empty Cart.
 */
public class ProductSearchTest extends BaseTestClass {
    @Test
    public void searchForTable() {
        // Search for "stainless work table".
        SearchResultsPage srp = homePage.searchForText("stainless work table");
        int numberOfPages = srp.getNumberOfPages();

        // Assert that all product entries on the page contain "table" in the title.
        // Returns on the last SRP.
        srp = verifyTitleContainsTextOnAllPages("table", srp);
        Assertions.assertEquals(srp.getCurrentPageNumber(), numberOfPages);

        // Grab the description from the last item, add item to the cart, then go to the cart.
        int numberOfResults = srp.getNumberOfResultsOnPage();
        String expectedDesc = srp.getResultNumberDescription(numberOfResults);
        CartPage cartPage = srp.addItemToCart(numberOfResults).navigateToCart();

        // Assert correct item was added to cart using saved description.
        Assertions.assertTrue(cartPage.cartContainsItem(expectedDesc));
        Assertions.assertEquals(1, cartPage.getCartSize());

        // Empty cart.
        cartPage.emptyCart();
        Assertions.assertEquals(0, cartPage.getCartSize());
    }

    // Helper method for verifying text in all result item descriptions on all SRPs.
    private SearchResultsPage verifyTitleContainsTextOnAllPages(String text, SearchResultsPage srp) {
        int pageCount = srp.getNumberOfPages();
        for (int i = 0; i < pageCount - 1; i++) {
            Boolean titleContainsText = srp.verifyProductTitlesContain(text);
            MatcherAssert.assertThat("All result titles should contain text", titleContainsText, equalTo(true));
            srp = srp.navigateToPage(i + 2);  // +2 due to pages being 1-indexed + next page.
        }
        return srp;
    }
}
