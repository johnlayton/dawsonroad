package au.com.afl;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Circle {

    private final int players;

    public Circle(final int players) {
        Preconditions.checkArgument(players > 1);

        this.players = players;
    }

    public static void main(final String... args) {
        Preconditions.checkArgument(args.length == 2);

        final int players = Integer.parseInt(args[0]);
        final int count = Integer.parseInt(args[1]);

        final Result result = new Circle(players).play(count);

        System.out.println(String.format("Winner = %d, Losers = %s",
                                         result.getWinner(), IntStream.of(result.getLosers()).boxed().collect(Collectors.toList())));
    }

    public Result play(final int count) {
        Preconditions.checkArgument(count > 0);

        final List<Integer> eliminated = new ArrayList<>();

        Player prev = link(players);
        Player curr = prev.next();

        for (int i = 1; i < players; i++) {
            for (int j = 1; j < count; j++) {
                prev = curr;
                curr = curr.next();
            }
            prev.next(curr.next());
            eliminated.add(curr.getId());
            curr = prev.next();
        }

        return new Result(curr.getId(), eliminated.stream().mapToInt(i -> i).toArray());
    }

    private Player link(final int n) {
        final Player first;
        Player curr;

        first = curr = new Player(1);
        for (int id = 2; id <= n; id++) {
            curr = curr.next(new Player(id));
        }
        curr.next(first);
        return curr;
    }

    static class Result {
        private final int winner;
        private final int[] losers;

        Result(int winner,
               int[] losers) {
            this.winner = winner;
            this.losers = losers;
        }

        public int getWinner() {
            return winner;
        }

        public int[] getLosers() {
            return losers;
        }
    }

    private class Player {
        private final int id;
        private Player next;

        Player(final int id) {
            this.id = id;
        }

        int getId() {
            return id;
        }

        Player next() {
            return next;
        }

        Player next(Player next) {
            return this.next = next;
        }
    }
}

