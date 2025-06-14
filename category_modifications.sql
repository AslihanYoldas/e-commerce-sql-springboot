SELECT * FROM public.category
ORDER BY category_id ASC ;

-- Adding main categories
INSERT INTO category (category_name) 
VALUES
  ('Electronics'),
  ('Fashion'),
  ('Furniture')
  ('Beauty and Personal Care')
  ('Accessories')
  ('Toys & Hobbies')
  ('Sports & Outdoor') ;

DELETE FROM category 
WHERE category_id IN(14,15,16);

--change ids to be the same of their row numbers
WITH updated_categories AS (
    SELECT category_id, ROW_NUMBER() OVER (ORDER BY category_id) AS new_id
    FROM category
)
UPDATE category
SET category_id = updated_categories.new_id
FROM updated_categories
WHERE category.category_id = updated_categories.category_id;

