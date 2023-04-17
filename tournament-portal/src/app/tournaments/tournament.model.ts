export class Tournament {
  _id!: string | null;
  name!: string;
  description!: string;
  discipline!: string;
  organizer!: string;
  date!: Date;
  location!: string;
  registrationLimit!: number;
  registrationDeadline!: Date;
  sponsors: string[] | undefined;
  mainImage: string | undefined;
  seedPlayers: number| undefined;
}
