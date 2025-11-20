import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileService, UserProfile } from '../../../core/services/profile.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-profile-avatar',
  standalone: true,
  imports: [CommonModule],
  template: `
    <button
      type="button"
      class="avatar"
      [title]="profile?.fullName || 'Perfil'"
      (click)="goToProfile()"
      aria-label="Abrir perfil"
    >
      <ng-container *ngIf="profile?.avatarDataUrl; else initials">
        <img [src]="profile?.avatarDataUrl" alt="Avatar do usuário" />
      </ng-container>
      <ng-template #initials>
        <span>{{ getInitials(profile) }}</span>
      </ng-template>
    </button>
  `,
  styles: [`
    .avatar {
      width: 42px;
      height: 42px;
      border-radius: 50%;
      border: 2px solid var(--border-color);
      display: inline-flex;
      align-items: center;
      justify-content: center;
      background: var(--card-bg);
      color: var(--primary-dark);
      font-weight: 700;
      font-size: 0.95rem;
      cursor: pointer;
      padding: 0;
      box-shadow: var(--shadow-sm);
      transition: var(--transition);
    }

    .avatar:hover {
      transform: translateY(-1px);
      box-shadow: var(--shadow-md);
    }

    .avatar img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      object-fit: cover;
    }
  `]
})
export class ProfileAvatarComponent {
  profile: UserProfile | null = this.profileService.profile;

  constructor(
    private readonly profileService: ProfileService,
    private readonly router: Router
  ) {
    this.profileService.profileChanges
      .pipe(takeUntilDestroyed())
      .subscribe(profile => (this.profile = profile));
  }

  getInitials(profile: UserProfile | null): string {
    if (!profile?.fullName) {
      return '?';
    }
    const [first, second] = profile.fullName.split(' ');
    const letters = (first?.[0] || '') + (second?.[0] || first?.[1] || '');
    return letters.toUpperCase();
  }

  goToProfile(): void {
    this.router.navigate(['/profile']);
  }
}
