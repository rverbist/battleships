package rmi.client.events;

import domain.Board;
import domain.Location;
import domain.MapSlot;
import domain.Player;

public interface IClientGameEventListener
{
    void onConnected(final Player player);
    void onDisconnected(final Player player);
    void onGlobalChatMessage(final String message);
    void onPlayerAssigned(final Player player);
    void onPlayerUnassigned(final Player player);
    void onPlayerJoinedTeam(final Player player, final int team);
    void onPlayerLeftTeam(final Player player);
    void onTeamChatMessage(final String message);
    void onPlayerIsReadyChanged(final Player player);
    void onGameStart(final int team, final Board board);
    void onGameTurnStart(final int team);
    void onPlayerAddSuggestion(final Player player, final Location location);
    void onPlayerRemoveSuggestion(final Player player);
    void onTeamHit(final int team, final int health, final int maximumHealth);
    void onGameTurnEnd(final int turn, final MapSlot slot, final Location location);
}