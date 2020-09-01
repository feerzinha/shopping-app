package com.moya.shopping


import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class ProductSearchFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun productSearchFragmentTest() {
        val actionMenuItemView = onView(
            allOf(
                withId(R.id.action_search), withContentDescription("Search"),
                childAtPosition(childAtPosition(withId(R.id.action_bar), 1), 0), isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val searchAutoComplete = onView(
            allOf(
                withId(R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(R.id.search_plate),
                        childAtPosition(withId(R.id.search_edit_frame), 1)
                    ), 0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete.perform(replaceText("Apple"))
        searchAutoComplete.perform(pressImeActionButton())
        Thread.sleep(8000)

        val productTitle = onView(
            allOf(
                withId(R.id.product_name), withText("Apple iPhone 6 32GB Grijs"),
                childAtPosition(childAtPosition(withId(R.id.product_item), 0), 1),
                isDisplayed()
            )
        )
        productTitle.check(matches(withText("Apple iPhone 6 32GB Grijs")))

        val productPrice = onView(
            allOf(
                withId(R.id.product_price), withText("$369.00"),
                childAtPosition(childAtPosition(withId(R.id.product_item), 0), 6),
                isDisplayed()
            )
        )
        productPrice.check(matches(withText("$369.00")))

        val cartIcon = onView(
            allOf(
                withIndex(withId(R.id.cart_button), 0),
                withId(R.id.cart_button), withContentDescription("Cart Icon"),
                childAtPosition(childAtPosition(withId(R.id.product_item), 0), 7),
                isDisplayed()
            )
        )
        cartIcon.check(matches(isDisplayed()))

        val productImage = onView(
            allOf(
                withIndex(withId(R.id.product_image), 0),
                withId(R.id.product_image), withContentDescription("Product Image"),
                childAtPosition(childAtPosition(withId(R.id.product_item), 0), 0),
                isDisplayed()
            )
        )
        productImage.check(matches(isDisplayed()))

        val firstDescription = onView(
            allOf(
                withId(R.id.product_description), withText("32 GB opslagcapaciteit"),
                childAtPosition(childAtPosition(withId(R.id.product_item), 0), 4),
                isDisplayed()
            )
        )
        firstDescription.check(matches(withText("32 GB opslagcapaciteit")))

        val secondDescription = onView(
            allOf(
                withIndex(withId(R.id.product_second_description), 0),
                withId(R.id.product_second_description), withText("4,7 inch Retina HD scherm"),
                childAtPosition(childAtPosition(withId(R.id.product_item), 0), 5),
                isDisplayed()
            )
        )
        secondDescription.check(matches(withText("4,7 inch Retina HD scherm")))

        val ratingBar = onView(
            allOf(
                withIndex(withId(R.id.product_rating), 0),
                withId(R.id.product_rating),
                childAtPosition(childAtPosition(withId(R.id.product_item), 0), 2),
                isDisplayed()
            )
        )
        ratingBar.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    private fun withIndex(
        matcher: Matcher<View?>,
        index: Int
    ): Matcher<View?>? {
        return object : TypeSafeMatcher<View>() {
            var currentIndex = 0
            var viewObjHash = 0

            @SuppressLint("DefaultLocale")
            override fun describeTo(description: Description) {
                description.appendText(String.format("with index: %d ", index))
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                if (matcher.matches(view) && currentIndex++ == index) {
                    viewObjHash = view.hashCode()
                }
                return view.hashCode() == viewObjHash
            }
        }
    }
}
