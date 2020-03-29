import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { MonsterbuilderSharedModule } from 'app/shared/shared.module';
import { MonsterbuilderCoreModule } from 'app/core/core.module';
import { MonsterbuilderAppRoutingModule } from './app-routing.module';
import { MonsterbuilderHomeModule } from './home/home.module';
import { MonsterbuilderEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    MonsterbuilderSharedModule,
    MonsterbuilderCoreModule,
    MonsterbuilderHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    MonsterbuilderEntityModule,
    MonsterbuilderAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class MonsterbuilderAppModule {}
