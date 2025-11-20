import { Component, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ThemeService } from '../../../core/services/theme.service';

type ThemeMode = 'light' | 'dark';

@Component({
  selector: 'app-theme-toggle',
  standalone: true,
  imports: [CommonModule],
  template: `
    <button
      type="button"
      class="theme-toggle"
      [class.is-dark]="theme === 'dark'"
      (click)="toggleTheme()"
      [attr.aria-label]="theme === 'dark' ? 'Ativar tema claro' : 'Ativar tema escuro'"
    >
      <span class="theme-toggle__icon sun">☀️</span>
      <span class="theme-toggle__track">
        <span class="theme-toggle__thumb"></span>
      </span>
      <span class="theme-toggle__icon moon">🌙</span>
    </button>
  `,
  styles: [`
    :host {
      display: inline-flex;
    }

    .theme-toggle {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      padding: 6px 12px;
      border-radius: 999px;
      border: 1px solid rgba(255, 255, 255, 0.35);
      background: var(--header-gradient);
      color: var(--header-text);
      cursor: pointer;
      transition: var(--transition);
      box-shadow: var(--shadow-sm);
      font-weight: 600;
      position: relative;
      overflow: hidden;
    }

    .theme-toggle:focus-visible {
      outline: 3px solid rgba(76, 175, 80, 0.35);
      outline-offset: 3px;
    }

    .theme-toggle:hover {
      border-color: rgba(255, 255, 255, 0.6);
      transform: translateY(-1px);
    }

    .theme-toggle__icon {
      font-size: 0.95rem;
      transition: opacity 0.3s ease, transform 0.3s ease;
    }

    .theme-toggle__track {
      width: 42px;
      height: 20px;
      border-radius: 999px;
      background: rgba(255, 255, 255, 0.35);
      position: relative;
      transition: background 0.3s ease;
    }

    .theme-toggle__thumb {
      position: absolute;
      top: 2px;
      left: 2px;
      width: 16px;
      height: 16px;
      border-radius: 50%;
      background: #ffffff;
      transition: transform 0.3s ease, background 0.3s ease;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
    }

    .theme-toggle.is-dark .theme-toggle__thumb {
      transform: translateX(22px);
      background: #e2e8f0;
    }

    .theme-toggle.is-dark .theme-toggle__track {
      background: rgba(15, 23, 42, 0.4);
    }

    .theme-toggle.is-dark .sun {
      opacity: 0.4;
      transform: scale(0.85);
    }

    .theme-toggle:not(.is-dark) .moon {
      opacity: 0.4;
      transform: scale(0.85);
    }

    :host-context([data-theme='dark']) .theme-toggle:focus-visible {
      outline-color: rgba(59, 130, 246, 0.45);
    }
  `]
})
export class ThemeToggleComponent implements OnDestroy {
  theme: ThemeMode = this.themeService.currentTheme;
  private readonly destroy$ = new Subject<void>();

  constructor(private readonly themeService: ThemeService) {
    this.themeService.themeChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe(theme => this.theme = theme);
  }

  toggleTheme(): void {
    this.themeService.toggleTheme();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
