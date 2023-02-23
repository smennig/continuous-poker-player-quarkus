package org.continuouspoker.player.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.continuouspoker.player.model.Bet;
import org.continuouspoker.player.model.Card;
import org.continuouspoker.player.model.Player;
import org.continuouspoker.player.model.Rank;
import org.continuouspoker.player.model.Table;

public class Strategy {

    public Bet decide(final Table table) {
        System.out.println(table);
        int bet = 0;

        var cards = getCards(table);
        System.out.println(cards);


        bet = isPair(getCommunityCards(table), getCards(table), bet, table);
        bet = checkFundsOrMinimumBet(bet, table);
        System.out.println("Betting: "+bet);
        return new Bet().bet(bet);
    }

    private int checkFundsOrMinimumBet(int bet, Table table){
        if (getPlayer(table).getStack() >= bet){
            return bet;
        }
        else if(table.getRound() == 1){
            return table.getMinimumBet();
        }
        else {
            return table.getMinimumBet();
        }
    }

    private  Bet goAllIn(final Table table) {
        return new Bet().bet(table.getPlayers().get(table.getActivePlayer()).getStack());
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

    private int isPair(List<Card> communityCards, List<Card> playerCards, int bet, final Table table) {
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
                System.out.println("We had a pair");
                bet+= table.getMinimumRaise();
            }
        }
        return bet;
    }

}
