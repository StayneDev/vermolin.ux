import { CommonModule } from '@angular/common';
import { Component, ElementRef, HostListener } from '@angular/core';

@Component({
  selector: 'app-header-menu',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="header-menu" [class.is-open]="menuOpen">
      <button
        type="button"
        class="header-menu__toggle"
        (click)="toggleMenu()"
        [attr.aria-expanded]="menuOpen"
        [attr.aria-label]="menuOpen ? 'Fechar menu' : 'Abrir menu'"
      >
        <span class="header-menu__icon" aria-hidden="true">
          <span></span>
          <span></span>
          <span></span>
        </span>
        Menu
      </button>
      <div class="header-menu__actions">
        <ng-content></ng-content>
      </div>
    </div>
  `,
  styles: [`
    :host {
      display: flex;
      align-items: center;
      position: relative;
      z-index: 40;
    }

    .header-menu {
      display: flex;
      align-items: center;
      gap: 12px;
      position: relative;
      z-index: inherit;
    }

    .header-menu__toggle {
      display: none;
      align-items: center;
      gap: 8px;
      padding: 8px 14px;
      border-radius: var(--radius-sm);
      border: 1px solid var(--border-color);
      background: var(--card-bg);
      color: var(--text-color);
      font-weight: 600;
      cursor: pointer;
      box-shadow: var(--shadow-sm);
      transition: var(--transition);
    }

    .header-menu__toggle:focus-visible {
      outline: 3px solid rgba(76, 175, 80, 0.35);
      outline-offset: 2px;
    }

    .header-menu__icon {
      display: inline-flex;
      flex-direction: column;
      gap: 4px;
    }

    .header-menu__icon span {
      width: 18px;
      height: 2px;
      background: currentColor;
      border-radius: 99px;
      transition: transform 0.2s ease;
    }

    .header-menu__actions {
      display: flex;
      gap: 12px;
      align-items: center;
      flex-wrap: wrap;
    }

    :host ::ng-deep app-role-nav {
      display: none;
    }

    @media (max-width: 768px) {
      :host {
        width: auto;
        justify-content: flex-end;
        flex: 0 0 auto;
      }

      .header-menu {
        width: auto;
        justify-content: flex-end;
        position: static;
      }

      .header-menu__toggle {
        display: inline-flex;
      }

      .header-menu__actions {
        position: absolute;
        top: calc(100% + 10px);
        right: 0;
        display: none;
        flex-direction: column;
        align-items: stretch;
        gap: 10px;
        padding: 16px;
        min-width: min(260px, 100vw);
        background: var(--card-bg);
        color: var(--text-color);
        border: 1px solid var(--border-color);
        border-radius: var(--radius-md);
        box-shadow: var(--shadow-lg);
        z-index: 120;
      }

      .header-menu.is-open .header-menu__actions {
        display: flex;
      }

      :host ::ng-deep app-role-nav {
        display: block;
      }

      :host ::ng-deep .header-menu__actions app-role-nav,
      :host ::ng-deep .header-menu__actions .btn,
      :host ::ng-deep .header-menu__actions app-theme-toggle {
        width: 100%;
      }
    }
  `]
})
export class HeaderMenuComponent {
  menuOpen = false;

  constructor(private readonly elementRef: ElementRef<HTMLElement>) {}

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    if (!this.menuOpen) {
      return;
    }

    const target = event.target as Node;
    if (!this.elementRef.nativeElement.contains(target)) {
      this.menuOpen = false;
    }
  }

  @HostListener('window:resize')
  onWindowResize(): void {
    if (this.menuOpen && window.innerWidth > 768) {
      this.menuOpen = false;
    }
  }
}
