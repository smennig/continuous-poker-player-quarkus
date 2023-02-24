package org.continuouspoker.player.logic;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.continuouspoker.player.model.Bet;
import org.continuouspoker.player.model.Card;
import org.continuouspoker.player.model.Player;
import org.continuouspoker.player.model.Rank;
import org.continuouspoker.player.model.Table;

public class Strategy {

    private List<Card> playerCards;
    private List<Card> communityCards;
    private Player player;
    private Table table;

    public Bet decide(final Table table) {
        init(table);

        System.out.println(table);
        int bet = 0;

        var cards = getCards(table);
        System.out.println(cards);

        bet = isPair(bet);
        bet = checkFundsOrMinimumBet(bet);
        System.out.println("Betting: " + bet);
        return new Bet().bet(bet);
    }

    private void init(final Table table) {
        this.table = table;
        player = getPlayer(table);
        playerCards = getCards(table);
        communityCards = getCommunityCards(table);
    }

    private int checkFundsOrMinimumBet(int bet) {
        if (player.getStack() >= bet) {
            return bet;
        } else if (table.getRound() == 0) {
            return table.getMinimumBet();
        } else {
            return table.getMinimumBet();
        }
    }

    private Bet goAllIn() {
        return new Bet().bet(player.getStack());
    }

    private List<Card> getCards(final Table table) {
        return getPlayer(table).getCards();
    }

    private Player getPlayer(final Table table) {
        return table.getPlayers().get(table.getActivePlayer());
    }

    private List<Card> getCommunityCards(final Table table) {
        return table.getCommunityCards();
    }

    private int isPair(int bet) {
        final Map<Rank, List<Card>> cardsByRank = Stream.concat(playerCards.stream(), communityCards.stream())
                .collect(Collectors.groupingBy(Card::getRank));


        for (Rank rank : cardsByRank.keySet()) {
            if (cardsByRank.get(rank) != null && cardsByRank.get(rank).size() >= 2) {
                System.out.println("We had a pair");
               return bet + table.getMinimumRaise();
            }
        }
        return bet;
    }

}
