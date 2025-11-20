import { Injectable, Inject, Renderer2, RendererFactory2 } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { BehaviorSubject, Observable } from 'rxjs';

type ThemeMode = 'light' | 'dark';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private renderer: Renderer2;
  private readonly storageKey = 'vermolinux-theme';
  private readonly themeSubject = new BehaviorSubject<ThemeMode>('light');

  constructor(
    @Inject(DOCUMENT) private readonly document: Document,
    rendererFactory: RendererFactory2
  ) {
    this.renderer = rendererFactory.createRenderer(null, null);
    const savedTheme = (localStorage.getItem(this.storageKey) as ThemeMode) || 'light';
    this.setTheme(savedTheme, false);
  }

  get themeChanges(): Observable<ThemeMode> {
    return this.themeSubject.asObservable();
  }

  get currentTheme(): ThemeMode {
    return this.themeSubject.value;
  }

  toggleTheme(): void {
    const nextTheme: ThemeMode = this.themeSubject.value === 'light' ? 'dark' : 'light';
    this.setTheme(nextTheme);
  }

  private setTheme(theme: ThemeMode, persist = true): void {
    this.themeSubject.next(theme);
    this.renderer.setAttribute(this.document.documentElement, 'data-theme', theme);

    if (persist) {
      localStorage.setItem(this.storageKey, theme);
    }

    this.animateThemeChange();
  }

  private animateThemeChange(): void {
    const body = this.document.body;
    body.classList.add('theme-transition');
    window.setTimeout(() => body.classList.remove('theme-transition'), 500);
  }
}
