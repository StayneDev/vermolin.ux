import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { LoginResponse, UserRole } from '../models/api.models';

export interface UserProfile {
  userId: number;
  username: string;
  role: UserRole;
  fullName: string;
  email: string;
  phone: string;
  bio: string;
  avatarDataUrl?: string | null;
}

@Injectable({ providedIn: 'root' })
export class ProfileService {
  private readonly storagePrefix = 'profile';
  private readonly profileSubject = new BehaviorSubject<UserProfile | null>(null);
  readonly profileChanges: Observable<UserProfile | null> = this.profileSubject.asObservable();

  constructor(private readonly authService: AuthService) {
    this.bootstrapProfile();
    this.authService.currentUser$.subscribe(() => this.bootstrapProfile());
  }

  get profile(): UserProfile | null {
    return this.profileSubject.value;
  }

  updateProfile(changes: Partial<UserProfile>): void {
    const current = this.ensureProfile();
    const updated = { ...current, ...changes };
    this.persist(updated);
    if (changes.fullName && changes.fullName !== current.fullName) {
      this.authService.updateCurrentUser({ fullName: changes.fullName });
    }
  }

  updateAvatar(avatarDataUrl: string | null): void {
    this.updateProfile({ avatarDataUrl });
  }

  private bootstrapProfile(): void {
    const user = this.authService.getCurrentUser();
    if (!user) {
      this.profileSubject.next(null);
      return;
    }

    const stored = this.readFromStorage(user.userId);
    if (stored) {
      this.profileSubject.next(stored);
      return;
    }

    const seeded = this.seedFromUser(user);
    this.persist(seeded);
  }

  private ensureProfile(): UserProfile {
    const profile = this.profileSubject.value;
    if (!profile) {
      throw new Error('Perfil não disponível. Faça login novamente.');
    }
    return profile;
  }

  private storageKey(userId: number): string {
    return `${this.storagePrefix}-${userId}`;
  }

  private readFromStorage(userId: number): UserProfile | null {
    const raw = localStorage.getItem(this.storageKey(userId));
    return raw ? JSON.parse(raw) as UserProfile : null;
  }

  private seedFromUser(user: LoginResponse): UserProfile {
    return {
      userId: user.userId,
      username: user.username,
      role: user.role,
      fullName: user.fullName,
      email: `${user.username}@vermolinux.app`,
      phone: '',
      bio: 'Adicione uma breve descrição sobre você.',
      avatarDataUrl: null
    };
  }

  private persist(profile: UserProfile): void {
    localStorage.setItem(this.storageKey(profile.userId), JSON.stringify(profile));
    this.profileSubject.next(profile);
  }
}
