package au.com.afl;

import au.com.afl.Circle.Result;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(DataProviderRunner.class)
public class CircleTest {

    @DataProvider
    public static Object[][] games() {
        return new Object[][] {
                { 2, 1, 2, new int[] { 1 } },
                { 2, 2, 1, new int[] { 2 } },
                { 3, 1, 3, new int[] { 1, 2 } },
                { 3, 2, 3, new int[] { 2, 1 } },
                { 10, 7, 9, new int[] { 7, 4, 2, 1, 3, 6, 10, 5, 8 } },
                { 10, 17, 3, new int[] { 7, 5, 6, 10, 8, 1, 2, 4, 9 } }
        };
    }

    @Test
    @UseDataProvider("games")
    public void shouldCalculateWinnerAndLosers(final int players,
                                               final int count,
                                               final int winner,
                                               final int[] losers) throws Exception {
        final Result result = new Circle(players).play(count);

        assertThat(result.getWinner()).isEqualTo(winner);
        assertThat(result.getLosers()).isEqualTo(losers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldValidatePlayers() throws Exception {
        new Circle(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldValidateCount() throws Exception {
        new Circle(2).play(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldValidateMainPlayers() throws Exception {
        Circle.main();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldValidateMainCount() throws Exception {
        Circle.main("2");
    }
}
