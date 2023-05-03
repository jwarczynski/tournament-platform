export interface Duel {
  id: string;
  tournamentId: string;
  player1: Player;
  player2: Player;
  winner: Player;
  duelStatus: 'APPROVED' | 'INCONSISTENT' | 'PENDING_QUORUM' | 'NOT_PLAYED';
  phase: number;
  duelNumber: number;
}

export interface Player {
  email: string;
  licenseNumber: string;
  rank: number;
  playerStatus: "LOOSE" |"WIN" | "PENDING_DUEL" | "DURING_GAMEPLAY";
}

export interface Match {
  players: Player[]
  result: number | null;
}
