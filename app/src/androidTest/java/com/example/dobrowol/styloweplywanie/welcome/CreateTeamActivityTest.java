package com.example.dobrowol.styloweplywanie.welcome;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import com.example.dobrowol.styloweplywanie.R;
import com.example.dobrowol.styloweplywanie.utils.ITeamDataUtils;
import com.example.dobrowol.styloweplywanie.utils.ITeamDataUtilsFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dobrowol on 16.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class CreateTeamActivityTest {


    @Captor
    ArgumentCaptor<String> teamNameCaptor;
    @Captor
    ArgumentCaptor<String> coachNameCaptor;
    @Rule
    @InjectMocks
    public ActivityTestRule<CreateTeamActivity> mActivityRule = new ActivityTestRule<>(CreateTeamActivity.class);
    @Mock(name = "teamDataUtilsFactory")
    ITeamDataUtilsFactory teamDataUtilsFactoryMock;
    @Mock
    ITeamDataUtils teamDataUtilsMock;

    @Test
    public void onClick() throws Exception {
        final String teamTestname = "Narwale";
        when(teamDataUtilsFactoryMock.createTeamDataUtils()).thenReturn(teamDataUtilsMock);
        onView(withId(R.id.editText_CreateTeamActivity_TeamName))
                .perform(clearText())
                .perform(typeText(teamTestname), closeSoftKeyboard());
        onView(withId(R.id.button_CreateTeamActivity)).perform(click());

        verify(teamDataUtilsMock).addTeam(teamNameCaptor.capture(), coachNameCaptor.capture());

        //assertEquals(teamTestname, captor.getValue());
        //when(teamDataUtilsMock.addTeam("Narwale"));
    }
}