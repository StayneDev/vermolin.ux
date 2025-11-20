import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ProfileService, UserProfile } from '../../core/services/profile.service';
import { ThemeToggleComponent } from '../../shared/components/theme-toggle/theme-toggle.component';
import { HeaderMenuComponent } from '../../shared/components/header-menu/header-menu.component';
import { ProfileAvatarComponent } from '../../shared/components/profile-avatar/profile-avatar.component';
import { RoleNavComponent } from '../../shared/components/role-nav/role-nav.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, ThemeToggleComponent, HeaderMenuComponent, ProfileAvatarComponent, RoleNavComponent],
  template: `
    <div class="profile-page">
      <header class="header">
        <div class="container header-content">
          <div>
            <p class="eyebrow">Perfil</p>
            <h1>Suas informações</h1>
            <p class="subtitle">Atualize seus dados pessoais e preferências do sistema.</p>
          </div>
          <div class="header-actions">
            <app-header-menu>
              <app-role-nav></app-role-nav>
              <app-theme-toggle></app-theme-toggle>
              <app-profile-avatar></app-profile-avatar>
              <button class="btn btn-secondary" (click)="goBack()">← Voltar</button>
            </app-header-menu>
          </div>
        </div>
      </header>

      <div class="container content-grid" *ngIf="profile; else missingProfile">
        <section class="card profile-summary">
          <div class="avatar-wrapper">
            <app-profile-avatar></app-profile-avatar>
            <button class="btn btn-outline" type="button" (click)="avatarInput.click()">Alterar foto</button>
            <input type="file" accept="image/*" class="sr-only" #avatarInput (change)="onAvatarSelected($event)" />
          </div>
          <div class="info">
            <h2>{{ profile.fullName }}</h2>
            <p class="role">{{ translateRole(profile.role) }}</p>
            <p class="username">&#64;{{ profile.username }}</p>
            <p class="bio">{{ profile.bio }}</p>
          </div>
        </section>

        <section class="card form-card">
          <form class="form-grid" (ngSubmit)="save()">
            <label>
              Nome completo
              <input type="text" class="form-control" [(ngModel)]="form.fullName" name="fullName" required />
            </label>

            <label>
              Email
              <input type="email" class="form-control" [(ngModel)]="form.email" name="email" required />
            </label>

            <label>
              Telefone
              <input type="tel" class="form-control" [(ngModel)]="form.phone" name="phone" />
            </label>

            <label class="full-width">
              Bio
              <textarea class="form-control" rows="3" [(ngModel)]="form.bio" name="bio"></textarea>
            </label>

            <div class="actions">
              <button class="btn btn-primary" type="submit">Salvar alterações</button>
              <button class="btn btn-outline" type="button" (click)="reset()">Descartar</button>
            </div>
          </form>
        </section>
      </div>

      <ng-template #missingProfile>
        <div class="container">
          <div class="card">
            <p>Não foi possível carregar os dados do perfil. Faça login novamente.</p>
            <button class="btn btn-primary" (click)="goBack()">Voltar</button>
          </div>
        </div>
      </ng-template>
    </div>
  `,
  styles: [`
    .profile-page {
      min-height: 100vh;
      background: var(--app-gradient);
      color: var(--text-color);
    }

    .header {
      background: var(--header-gradient);
      color: var(--header-text);
      padding: 2rem 0;
      box-shadow: var(--shadow-md);
      position: relative;
      z-index: 80;
    }

    .header-content {
      display: flex;
      justify-content: space-between;
      gap: 1rem;
      align-items: center;
      flex-wrap: wrap;
      position: relative;
      z-index: 90;
    }

    .header-actions {
      margin-left: auto;
      display: flex;
      justify-content: flex-end;
      flex: 0 0 auto;
      width: auto;
    }

    .eyebrow {
      text-transform: uppercase;
      letter-spacing: 0.2em;
      font-weight: 700;
      margin-bottom: 0.25rem;
      font-size: 0.8rem;
    }

    .subtitle {
      margin-top: 0.5rem;
      opacity: 0.85;
    }

    .content-grid {
      display: grid;
      grid-template-columns: minmax(280px, 360px) 1fr;
      gap: 1.5rem;
      padding-block: 2rem;
    }

    .profile-summary {
      display: flex;
      flex-direction: column;
      gap: 1.25rem;
      position: sticky;
      top: 24px;
    }

    .avatar-wrapper {
      display: flex;
      flex-direction: column;
      gap: 0.75rem;
      align-items: flex-start;
    }

    .avatar-wrapper app-profile-avatar ::ng-deep .avatar {
      width: 68px;
      height: 68px;
      font-size: 1.25rem;
    }

    .info h2 {
      font-family: var(--font-heading);
      font-size: 1.75rem;
      margin-bottom: 0.25rem;
    }

    .role {
      font-weight: 600;
      color: var(--primary);
    }

    .username {
      color: var(--muted-text);
      margin-bottom: 0.75rem;
    }

    .bio {
      color: var(--text-color);
    }

    .form-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
      gap: 16px;
    }

    .full-width {
      grid-column: 1 / -1;
    }

    .actions {
      grid-column: 1 / -1;
      display: flex;
      gap: 12px;
      flex-wrap: wrap;
    }

    .sr-only {
      position: absolute;
      width: 1px;
      height: 1px;
      padding: 0;
      margin: -1px;
      overflow: hidden;
      clip: rect(0, 0, 0, 0);
      border: 0;
    }

    @media (max-width: 900px) {
      .content-grid {
        grid-template-columns: 1fr;
      }

      .profile-summary {
        position: static;
      }

      .header-content {
        flex-direction: column;
        align-items: flex-start;
      }

      .header-actions {
        width: 100%;
        margin-left: 0;
        margin-top: 1rem;
        justify-content: flex-end;
      }
    }
  `]
})
export class ProfileComponent {
  profile: UserProfile | null = this.profileService.profile;
  form: Pick<UserProfile, 'fullName' | 'email' | 'phone' | 'bio'> = this.profile
    ? { fullName: this.profile.fullName, email: this.profile.email, phone: this.profile.phone, bio: this.profile.bio }
    : { fullName: '', email: '', phone: '', bio: '' };

  constructor(
    private readonly profileService: ProfileService,
    private readonly router: Router
  ) {
    this.profileService.profileChanges.subscribe(profile => {
      this.profile = profile;
      if (profile) {
        this.form = {
          fullName: profile.fullName,
          email: profile.email,
          phone: profile.phone,
          bio: profile.bio
        };
      }
    });
  }

  save(): void {
    if (!this.profile) {
      return;
    }
    this.profileService.updateProfile({
      fullName: this.form.fullName ?? this.profile.fullName,
      email: this.form.email ?? this.profile.email,
      phone: this.form.phone ?? this.profile.phone,
      bio: this.form.bio ?? this.profile.bio
    });
  }

  reset(): void {
    if (this.profile) {
      this.form = {
        fullName: this.profile.fullName,
        email: this.profile.email,
        phone: this.profile.phone,
        bio: this.profile.bio
      };
    }
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }

  onAvatarSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    if (!file) {
      return;
    }
    const reader = new FileReader();
    reader.onload = () => {
      this.profileService.updateAvatar(reader.result as string);
    };
    reader.readAsDataURL(file);
    input.value = '';
  }

  translateRole(role: UserProfile['role']): string {
    const map = {
      'GERENTE': 'Gerente',
      'ESTOQUISTA': 'Estoquista',
      'CAIXA': 'Caixa'
    } as const;
    return map[role];
  }
}
