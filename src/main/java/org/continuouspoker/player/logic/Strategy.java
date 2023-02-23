package org.continuouspoker.player.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.continuouspoker.player.model.Bet;
import org.continuouspoker.player.model.Card;
import org.continuouspoker.player.model.Rank;
import org.continuouspoker.player.model.Table;

public class Strategy {

    public Bet decide(final Table table) {
        System.out.println(table);

        var cards = getCards(table);
        System.out.println(cards);

        if (isPair(getCommunityCards(table), getCards(table))){
            System.out.println("Is Pair "+ getCards(table));
            return new Bet().bet(table.getMinimumBet() +40);
        }
        return  new Bet().bet(table.getMinimumBet());
    }

    private static Bet goAllIn(final Table table) {
        return new Bet().bet(table.getPlayers().get(table.getActivePlayer()).getStack());
    }

    private List<Card> getCards(final Table table) {
        return table.getPlayers()
                .get(table.getActivePlayer()).getCards();
    }

    private List<Card> getCommunityCards(final Table table) {
        return table.getCommunityCards();
    }

    private boolean isPair(List<Card> communityCards, List<Card> playerCards) {
        communityCards.addAll(playerCards);
        Map<Rank, Integer> pairs = new HashMap<>();
        for (Card card : communityCards) {
            final Integer pair = pairs.get(card.getRank());
            if (pair == null) {
                pairs.put(card.getRank(), 1);
            } else {
                pairs.put(card.getRank(), pair + 1);
            }
        }
        for (Rank rank : pairs.keySet()) {
            if (pairs.get(rank) != null && pairs.get(rank) >= 2) {
                return true;
            }
        }
        return false;
    }

}
