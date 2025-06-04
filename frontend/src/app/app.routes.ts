import {Routes} from '@angular/router';
import {BalanceComponent} from "./components/balance/balance.component";
import {TransactionComponent} from "./components/transaction/transaction.component";
import {StatementComponent} from "./components/statement/statement.component";

export const routes: Routes = [
  {path: 'balance', component: BalanceComponent},
  {path: 'deposit-withdraw', component: TransactionComponent},
  {path: 'statement', component: StatementComponent},

];
