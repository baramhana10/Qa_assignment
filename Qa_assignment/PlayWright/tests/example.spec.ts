import { test, expect } from '@playwright/test';

test('Login, add items, view item, and logout (using allocators only)', async ({ page }) => {

  await page.goto('https://www.saucedemo.com/');
  await expect(page).toHaveTitle('Swag Labs');
  const usernameInput = page.locator('[data-test="username"]');
  const passwordInput = page.locator('[data-test="password"]');
  const loginButton = page.locator('[data-test="login-button"]');
  const addBikeLight = page.locator('[data-test="add-to-cart-sauce-labs-bike-light"]');
  const addBackpack = page.locator('[data-test="add-to-cart-sauce-labs-backpack"]');
  const cartButton = page.locator('[data-test="shopping-cart-link"]');
  const itemTitle = page.locator('[data-test="item-0-title-link"]');
  const backToProducts = page.locator('[data-test="back-to-products"]');
  const menuButton = page.getByRole('button', { name: 'Open Menu' });
  const logoutLink = page.locator('[data-test="logout-sidebar-link"]');


  await usernameInput.fill('standard_user');
  await page.waitForTimeout(1000);
  await passwordInput.fill('secret_sauce');
  await page.waitForTimeout(1000);
  await loginButton.click();
  await page.waitForTimeout(1000);
  await expect(page).toHaveURL(/inventory\.html/);


  await addBikeLight.click();
  await page.waitForTimeout(1000);
  await addBackpack.click();
  await page.waitForTimeout(1000);

  // View item
  await cartButton.click();
  await page.waitForTimeout(1000);
  await itemTitle.click();
  await page.waitForTimeout(1000);
  await backToProducts.click();
  await page.waitForTimeout(1000);

  // Logout
  await menuButton.click();
  await page.waitForTimeout(1000);
  await logoutLink.click();
});
