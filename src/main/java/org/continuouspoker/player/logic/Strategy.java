package org.continuouspoker.player.logic;

import java.util.List;

import org.continuouspoker.player.model.Bet;
import org.continuouspoker.player.model.Card;
import org.continuouspoker.player.model.Table;

public class Strategy {

   public Bet decide(final Table table) {
      System.out.println(table);

      var cards = getCards(table);
      System.out.println(cards);

      return goAllIn(table);
   }

   private static Bet goAllIn(final Table table) {
      return new Bet().bet(table.getPlayers().get(table.getActivePlayer()).getStack());
   }

   private  List<Card> getCards(final Table table) {
      return table.getPlayers()
              .stream()
              .filter(player1 -> player1.getName().equals("die-planlosen-bluffer"))
              .map(player1 -> player1.getCards())
              .findFirst()
              .get();
   }
   private  List<Card> getCommunityCards(final Table table) {
      return table.getCommunityCards();
   }

   private boolean isPair (List<Card> communityCards,List<Card> playerCards  ){
      communityCards.addAll(playerCards);

      for(Card card : communityCards){

      }
      return false;
   }
/*   private int countPairs (List<Card> communityCards,List<Card> playerCards  ){
      communityCards.addAll(playerCards)
   }*/

}
